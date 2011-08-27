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
package com.logisima.javagit.cli.show;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.ICommandResponse;
import com.logisima.javagit.cli.IParser;

/**
 * Parser class to parse the output generated by git show; and return a <code>GitShowResponse</code> object.
 */
public class GitShowParser implements IParser {

    public void parseLine(String line) {
        // TODO Auto-generated method stub

    }

    public void processExitCode(int code) {
        // TODO Auto-generated method stub

    }

    public ICommandResponse getResponse() throws JavaGitException {
        // TODO Auto-generated method stub
        return null;
    }

}
