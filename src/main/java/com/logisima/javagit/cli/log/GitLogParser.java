/**
 *  This file is part of LogiSima (http://www.logisima.com).
 *
 *  JavGit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavGit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with LogiSima-Common.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli.log;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;

/**
 * Parser class to parse the output generated by git log; and return a <code>GitLogResponse</code> object.
 */
public class GitLogParser extends Parser {

    private int            linesAdded   = 0;
    private int            linesDeleted = 0;
    private boolean        canCommit    = false;
    private String         filename     = null;
    private String[]       tmp;
    private GitLogResponse response     = new GitLogResponse();

    /**
     * Add the final parsed commit. and returns the response of git log execution.
     */
    public ICommandResponse getResponse() throws JavaGitException {
        response.addCommit();
        return this.response;
    }

    /**
     * Parses a line at a time from the commandline execution output of git log
     */
    public void parseLine(String line) {
        if (line.length() == 0) {
            return;
        }

        // commit
        if (line.startsWith("commit")) {
            if (canCommit) {
                response.addCommit();

            }
            canCommit = true;
            tmp = line.split(" ");
            response.setSha(line.substring(tmp[0].length()).trim());
        }
        // merge (optional)
        else if (line.startsWith("Merge")) {
            List<String> mergeDetails = new ArrayList<String>();
            tmp = line.split(" ");
            StringTokenizer st = new StringTokenizer(line.substring(tmp[0].length()));
            while (st.hasMoreTokens()) {
                mergeDetails.add(st.nextToken().trim());
            }

            response.setMergeDetails(mergeDetails);
            mergeDetails = null;
        }
        // Author
        else if (line.startsWith("Author")) {
            tmp = line.split(" ");
            response.setAuthor(line.substring(tmp[0].length()).trim());
        }
        // Date
        else if (line.startsWith("Date")) {
            tmp = line.split(" ");
            response.setDate(line.substring(tmp[0].length()).trim());
        }
        // message or fileDetails (always starts with an int)
        else {
            StringTokenizer st = new StringTokenizer(line);
            try {
                linesAdded = Integer.parseInt(st.nextToken());
                linesDeleted = Integer.parseInt(st.nextToken());
                filename = st.nextToken().toString();
                response.addFile(filename, linesAdded, linesDeleted);
            } catch (NumberFormatException nfe) {
                try {
                    if (st.nextToken().equals("-")) {
                        linesAdded = 0;
                        linesDeleted = 0;
                        filename = st.nextToken().toString();
                        response.addFile(filename, linesAdded, linesDeleted);
                    }
                    else {
                        response.setMessage(line);
                    }
                } catch (NoSuchElementException nsee) {
                    response.setMessage(line);
                }
            } catch (NoSuchElementException nsee) {
                response.setMessage(line);
            } catch (Exception e) {
                response.setMessage(line);
            }
        }
    }

    public void processExitCode(int code) {
        // TODO Auto-generated method stub

    }
}
