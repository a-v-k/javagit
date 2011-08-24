/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.cli.show;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.api.object.Ref;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitShow</code> interface.
 */
public class GitShow {

    public GitShowResponse show(File repositoryPath, GitShowOptions options) throws JavaGitException {
        return show(repositoryPath, options, null, null);
    }

    public GitShowResponse show(File repositoryPath, GitShowOptions options, Ref revision) throws JavaGitException {
        return show(repositoryPath, options, null, revision);
    }

    public GitShowResponse show(File repositoryPath, GitShowOptions options, File path, Ref revision)
            throws JavaGitException {
        CheckUtilities.checkNullArgument(repositoryPath, "repository");
        CheckUtilities.validateArgumentRefType(revision, Ref.RefType.SHA1, "revision");

        GitShowParser parser = new GitShowParser();
        List<String> command = buildCommand(repositoryPath, options, path, revision);
        try {
            GitShowResponse response = (GitShowResponse) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        // TODO besim
        return null;
    }

    /**
     * Constructor of the command line.
     */
    protected List<String> buildCommand(File repositoryPath, GitShowOptions options, File file, Ref revision) {

        List<String> cmd = new ArrayList<String>();
        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("show");

        if (file != null && revision != null) {
            cmd.add(revision.getName() + ":" + file.getPath());
        }
        else {
            if (revision != null) {
                cmd.add(revision.getName());
            }
        }

        if (null != options) {
            // TODO there is no options
        }

        return cmd;
    }
}