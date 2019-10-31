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

import com.example.getstarted.daos.PersonDao;
import com.example.getstarted.objects.Person;
import com.example.getstarted.util.CloudStorageHelper;
import com.google.common.base.Strings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

// [START example]
@SuppressWarnings("serial")
public class AddSocialLinkServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        PersonDao dao = (PersonDao) this.getServletContext().getAttribute("dao");
        try {
            Person person = dao.readPerson(Long.decode(req.getParameter("id")));
            req.setAttribute("person", person);
            req.setAttribute("action", "Edit");
            req.setAttribute("destination", "update");
            req.setAttribute("page", "form");
            req.getRequestDispatcher("/base.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error loading person for editing", e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        PersonDao dao = (PersonDao) this.getServletContext().getAttribute("dao");

        String link = req.getParameter("sociallink");
        Long id =Long.decode(req.getParameter("id"));

        try {
            Person oldPerson = dao.readPerson(id);

            oldPerson.addSocialLink(link);
            // [START personBuilder]
            Person person = new Person.Builder()
                    .last(oldPerson.getLast())
                    .description(oldPerson.getDescription())
                    .first(oldPerson.getFirst())
                    .imageUrl(oldPerson.getImageUrl())
                    .id(id)
                    .createdBy(oldPerson.getCreatedBy())
                    .createdById(oldPerson.getCreatedById())
                    .socialLinks(oldPerson.getSocialLinks())
                    .build();
            // [END personBuilder]

            dao.updatePerson(person);


        req.setAttribute("sociallink", link);

        resp.sendRedirect("/read?id=" + id+"&sociallink="+link);
        } catch (Exception e) {
            throw new ServletException("Error updating person", e);
        }
    }
}
// [END example]

