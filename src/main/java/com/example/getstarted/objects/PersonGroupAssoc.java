/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.objects;

// [START example]
public class PersonGroupAssoc {

  private Long personId;
  private Long groupId;
  private String createdBy;
  private String createdById;

 
  private Long id;
  
  
  // [START keys]
  public static final String PERSON_ID = "personId";
  public static final String GROUP_ID = "groupId";
  public static final String CREATED_BY = "createdBy";
  public static final String CREATED_BY_ID = "createdById";
  public static final String ID = "id";
 
  // [END keys]

  // [START constructor]
  // We use a Builder pattern here to simplify and standardize construction of Group objects.
  private PersonGroupAssoc(Builder builder) {
    this.personId = builder.personId;
    this.groupId = builder.groupId;
    this.createdBy = builder.createdBy;
    this.createdById = builder.createdById;

    this.id = builder.id;
  }
  // [END constructor]

  // [START builder]
  public static class Builder {
    private Long personId;
    private Long groupId;

    private String createdBy;
    private String createdById;
    private Long id;

    public Builder personId(Long personId) {
      this.personId = personId;
      return this;
    }
    
    public Builder groupId(Long groupId) {
      this.groupId = groupId;
      return this;
    }

    public Builder createdBy(String createdBy) {
      this.createdBy = createdBy;
      return this;
    }

    public Builder createdById(String createdById) {
      this.createdById = createdById;
      return this;
    }


    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public PersonGroupAssoc build() {
      return new PersonGroupAssoc(this);
    }
  }

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }
  
  public Long getGroupId() {
    return groupId;
  }
  
  public void setGroupId(Long groupId) {
    this.groupId = groupId;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getCreatedById() {
    return createdById;
  }

  public void setCreatedById(String createdById) {
    this.createdById = createdById;
  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  // [END builder]
  @Override
  public String toString() {
    return
        "PersonId: " + personId + ", Added by: " + createdBy;
  }
}
// [END example]
