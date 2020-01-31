/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.spanner.Repository;

// [START spring_data_spanner_template_sample]

import com.example.spanner.tables.Album;
import com.example.spanner.tables.Singer;
import com.google.cloud.spanner.KeySet;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spanner.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.data.spanner.core.SpannerTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * A quick start code for Spring Data Cloud Spanner. It demonstrates how to use SpannerTemplate to
 * execute DML and SQL queries, save POJOs, and read entities.
 */
// [START spring_data_spanner_template_sample]
@Component
public class SpannerTemplateSinger {

  @Autowired
  SpannerTemplate spannerTemplate;

  public List<Singer> insertSinger(Singer singer) {
    // Insert a singer into the Singers table.
    this.spannerTemplate.insert(singer);

    List<Singer> allSingers = new ArrayList<>();
    Singer sin;

    Statement statement = Statement.newBuilder(
            "SELECT singerId, firstName, lastName FROM Singers WHERE singerId = @singerId ")
            .bind("singerId")
            .to(singer.getSingerId())
            .build();
    ResultSet rs = this.spannerTemplate.executeQuery(statement,null);
    while (rs.next()){
      sin = new Singer();
      sin.setSingerId(rs.getLong("singerId"));
      sin.setFirstName(rs.getString("firstName"));
      sin.setLastName(rs.getString("lastName"));
      allSingers.add(sin);
    }

    // Read all of the singers in the Singers table.
    //List<Singer> allSingers = this.spannerTemplate
    //    .query(Singer.class, Statement.of("SELECT singerId,firstName, FROM Singers"), null);

    return allSingers;
  }

  public List<Singer> getAllSinger(int limitedNumbner){
    List<Singer> allSingers = new ArrayList<>();
    List<Album> albums ;
    Singer sin;
    Album alb;

    Statement statement = Statement.newBuilder(
            "SELECT singerId, firstName, lastName FROM Singers limit @limitedNumber ")
            .bind("limitedNumber")
            .to(limitedNumbner)
            .build();
    ResultSet rs = this.spannerTemplate.executeQuery(statement,null);
    while (rs.next()){
      sin = new Singer();
      sin.setSingerId(rs.getLong("singerId"));
      sin.setFirstName(rs.getString("firstName"));
      sin.setLastName(rs.getString("lastName"));

      Statement albumStatement = Statement.newBuilder(
              "SELECT albumId, albumTitle, marketingBudget  FROM Albums where singerId =  @singerId")
              .bind("singerId")
              .to(sin.getSingerId())
              .build();
      ResultSet albumRs = this.spannerTemplate.executeQuery(albumStatement,null);
      albums = new ArrayList<>();
      while(albumRs.next()){
        albums.add(new Album(sin.getSingerId(),albumRs.getLong("albumId"),albumRs.getString("albumTitle"), albumRs.getLong("marketingBudget")));
      }
      sin.setAlbums(albums);
      allSingers.add(sin);
    }

    return allSingers;
  }

}
// [END spring_data_spanner_template_sample]
