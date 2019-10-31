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

package com.example.getstarted.basicactions;

import com.example.getstarted.daos.GroupDao;
import com.example.getstarted.daos.CloudSqlDao;
import com.example.getstarted.daos.GroupDatastoreDao;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Result;
import com.example.getstarted.util.CloudStorageHelper;

import com.google.common.base.Strings;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// [START example]
@SuppressWarnings("serial")
public class ListGroupServlet extends HttpServlet {

  @Override
  public void init() throws ServletException {
    GroupDao groupDao = null;

    CloudStorageHelper storageHelper = new CloudStorageHelper();

    // Creates the DAO based on the Context Parameters
    String storageType = this.getServletContext().getInitParameter("personshelf.storageType");
    switch (storageType) {
      case "datastore":
        groupDao = new GroupDatastoreDao();
        break;
      case "cloudsql":
        /*
        try {
          // Use this url when using dev appserver, but connecting to Cloud SQL
          String connect = this.getServletContext().getInitParameter("sql.urlRemote");
          if (connect.contains("localhost")) {
            // Use this url when using a local mysql server
            connect = this.getServletContext().getInitParameter("sql.urlLocal");
          } else if (System.getProperty("com.google.appengine.runtime.version")
              .startsWith("Google App Engine/")) {
            // Use this url when on App Engine, connecting to Cloud SQL.
            // Uses a special adapter because of the App Engine sandbox.
            connect = this.getServletContext().getInitParameter("sql.urlRemoteGAE");
          }
         
          dao = new CloudSqlDao(connect);
        } catch (SQLException e) {
          throw new ServletException("SQL error", e);
        }
        */
        break;
      default:
        throw new IllegalStateException(
            "Invalid storage type. Check if personshelf.storageType property is set.");
    }
    this.getServletContext().setAttribute("groupdao", groupDao);
    this.getServletContext().setAttribute("storageHelper", storageHelper);
    this.getServletContext().setAttribute(
        "isCloudStorageConfigured",  // Hide upload when Cloud Storage is not configured.
        !Strings.isNullOrEmpty(getServletContext().getInitParameter("personshelf.bucket")));
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,
      ServletException {
    GroupDao dao = (GroupDao) this.getServletContext().getAttribute("groupdao");
    String startCursor = req.getParameter("cursor");
    List<Group> groups = null;
    String endCursor = null;
    try {
      Result<Group> result = dao.listGroups(startCursor);
      groups = result.result;
      endCursor = result.cursor;
    } catch (Exception e) {
      throw new ServletException("Error listing groups", e);
    }
    req.getSession().getServletContext().setAttribute("groups", groups);
    StringBuilder groupNames = new StringBuilder();
    for (Group group : groups) {
      groupNames.append(group.getName() + " ");
    }
    req.setAttribute("cursor", endCursor);
    req.setAttribute("page", "listgroup");
    req.getRequestDispatcher("/base.jsp").forward(req, resp);
  }
}
// [END example]
