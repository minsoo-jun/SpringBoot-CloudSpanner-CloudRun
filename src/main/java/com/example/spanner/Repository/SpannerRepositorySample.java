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

// [START spring_data_spanner_repository_sample]

import com.example.spanner.Repository.SingerRepository;
import com.example.spanner.tables.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * A quick start code for Spring Data Cloud Spanner.
 * It demonstrates how to use a SpannerRepository to execute read-write queries
 * generated from interface definitions.
 *
 */
@Component
public class SpannerRepositorySample {

  @Autowired
  SingerRepository singerRepository;

  public void runRepositoryExample() {
    List<Singer> lastNameSingers = this.singerRepository.findByLastName("a last name");

    int fistNameCount = this.singerRepository.countByFirstName("a first name");

    int deletedLastNameCount = this.singerRepository.deleteByLastName("a last name");
  }

}
// [END spring_data_spanner_repository_sample]
