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

package com.example.spanner.tables;

import org.springframework.cloud.gcp.data.spanner.core.mapping.NotMapped;
import org.springframework.cloud.gcp.data.spanner.core.mapping.PrimaryKey;
import org.springframework.cloud.gcp.data.spanner.core.mapping.Table;

import java.util.List;

/**
 * An entity class representing an Album.
 */
@Table(name = "Albums")
public class Album {

  @PrimaryKey
  private long singerId;

  @PrimaryKey(keyOrder = 2)
  private long albumId;

  private String albumTitle;

  private long marketingBudget;

  @NotMapped
  private List<Album> relatedAlbums;

  public Album(long singerId, long albumId, String albumTitle, long marketingBudget) {
    this.singerId = singerId;
    this.albumId = albumId;
    this.albumTitle = albumTitle;
    this.marketingBudget = marketingBudget;
  }

  public long getSingerId() {
    return singerId;
  }

  public void setSingerId(long singerId) {
    this.singerId = singerId;
  }

  public long getAlbumId() {
    return albumId;
  }

  public void setAlbumId(long albumId) {
    this.albumId = albumId;
  }

  public String getAlbumTitle() {
    return albumTitle;
  }

  public void setAlbumTitle(String albumTitle) {
    this.albumTitle = albumTitle;
  }

  public long getMarketingBudget() {
    return marketingBudget;
  }

  public void setMarketingBudget(long marketingBudget) {
    this.marketingBudget = marketingBudget;
  }

  public List<Album> getRelatedAlbums() {
    return relatedAlbums;
  }

  public void setRelatedAlbums(List<Album> relatedAlbums) {
    this.relatedAlbums = relatedAlbums;
  }
}

