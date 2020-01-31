package com.example.spanner.Controller;

import com.example.spanner.Repository.SpannerRepositorySample;
import com.example.spanner.Repository.SpannerSchemaToolsSample;
import com.example.spanner.Repository.SpannerTemplateSample;
import com.example.spanner.Repository.SpannerTemplateSinger;
import com.example.spanner.tables.Album;
import com.example.spanner.tables.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.spanner.core.SpannerTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Controller
public class HelloController {

    @Autowired
    SpannerSchemaToolsSample spannerSchemaToolsSample;

    @Autowired
    SpannerTemplateSample spannerTemplateSample;

    @Autowired
    SpannerTemplateSinger spannerTemplateSinger;

    @Autowired
    SpannerRepositorySample spannerRepositorySample;

    @Autowired
    SpannerTemplate spannerTemplate;

    @GetMapping("/index")
    public String hello(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model){
        model.addAttribute("name", name);
        List<Singer> result = spannerTemplateSinger.getAllSinger(100);
        model.addAttribute("singer",result);
        return "index";
    }

    @GetMapping("init")
    public String initSpanner(Model model){
        spannerSchemaToolsSample.createTableIfNotExists();

        Singer singer = new Singer();
        singer.setSingerId(1L);
        singer.setFirstName("John");
        singer.setLastName("Doe");
        singer.setAlbums(Arrays.asList(new Album(1L, 10L, "album1", 11L),
                new Album(1L, 20L, "album2", 12L)));

        spannerTemplateSample.runTemplateExample(singer);
        spannerRepositorySample.runRepositoryExample();

        model.addAttribute("message", "Init done");

        return "index";
    }

    @GetMapping("drop")
    public String dropDBSpanner(Model model){
        spannerSchemaToolsSample.dropTables();

        model.addAttribute("message", "Drop done");
        return "index";
    }

    @GetMapping("addSinger")
    public String addSinger(Model model){

        Random r = new Random();
        Singer singer = new Singer();
        singer.setSingerId(r.nextInt());
        singer.setFirstName("John" + singer.getSingerId());
        singer.setLastName("Doe" + singer.getSingerId());

        singer.setAlbums(Arrays.asList(new Album(singer.getSingerId(), 10L, "album1", 11L),
                new Album(singer.getSingerId(), 20L, "album2", 12L)));

        //spannerTemplateSample.runTemplateExample(singer);
        List<Singer> result = spannerTemplateSinger.insertSinger(singer);
        //spannerRepositorySample.runRepositoryExample();

        model.addAttribute("message", singer.getSingerId()+ " was added");
        model.addAttribute("singer",result);

        return "index";
    }

    @GetMapping("removeSinger")
    public String removeSinger(@RequestParam(name="s_id", required=true, defaultValue="1") int s_id, Model model){
        Singer singer = new Singer();
        singer.setSingerId(s_id);
        spannerTemplate.delete(singer);

        model.addAttribute("message", s_id + " was deleted");
        List<Singer> result = spannerTemplateSinger.getAllSinger(100);
        model.addAttribute("singer",result);
        return "index";
    }
}
