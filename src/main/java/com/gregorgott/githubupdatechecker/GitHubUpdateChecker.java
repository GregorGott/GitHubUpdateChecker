/*
 * Copyright (C) 2022  GregorGott
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gregorgott.githubupdatechecker;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.net.URL;

/**
 * {@code GitHubUpdateChecker} checks if a new update for a given GitHub repository is available.
 * To check the constructor needs the <em>author</em>, <em>repository name</em>, <em>current version</em> and an array
 * with chars which should be ignored.
 * The implementation of this class could look like this:
 * <pre>
 *     public class MyClass {
 *         public MyClass() {
 *             String currentVersion = "1.0.0";
 *             String author = "MyName";
 *             String repoName = "MyRepo";
 *             char[] ignoredChars = new char[]{'.', 'v'};
 *
 *             GitHubUpdateChecker guc = new GitHubUpdateChecker(currentVersion, author, repoName, ignoredChars);
 *             boolean updateAvailable = gitHubUpdateChecker.isUpdateAvailable();
 *
 *             System.out.println(updateAvailable);
 *         }
 *     }
 * </pre>
 * This prints a <em>boolean</em> if an update for the repository "MyRepo" from "MyName" is available.
 *
 * @author GregorGott
 * @version 1.0.0
 * @since 2022-07-24
 */
public class GitHubUpdateChecker {
    private final int currentTag;
    private final char[] ignoredChars;
    private final String urlToRepo;

    /**
     * Sets the url to the repository, ignored chars and sets the {@code currentTag} to the {@code currentVersion}
     * without the {@code ignoredChars}.
     *
     * @param currentVersion    the current version as string.
     * @param author            the author of the repository.
     * @param repoName          the name of the repository.
     * @param ignoredChars      a characters array with all ignored chars (e.g. {'.', 'v'} if your tag is "v1.0.0").
     */
    public GitHubUpdateChecker(String currentVersion, String author, String repoName, char[] ignoredChars) {
        this.urlToRepo = String.format("https://api.github.com/repos/%s/%s/releases/latest", author, repoName);
        this.ignoredChars = ignoredChars;
        currentTag = getVersionFromTag(currentVersion);
    }

    /**
     * Makes an HTTP request, gets the value of the key <em>tag_name</em> and transforms it to an integer by removing
     * all characters which are in {@code ignoredLines}.
     * @return a boolean if the newest version is bigger than the current.
     */
    public boolean isUpdateAvailable() {
        try {
            URL url = new URL(urlToRepo);
            JSONTokener jsonTokener = new JSONTokener(url.openStream());
            JSONObject jsonObject = new JSONObject(jsonTokener);
            String newestTag = jsonObject.getString("tag_name");

            int newestVersion = getVersionFromTag(newestTag);

            return currentTag < newestVersion;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Replaces all characters in {@code ignoredLines} with empty places and returns the tag as an integer.
     * @param s the input with characters to remove.
     * @return the tag as version number as an integer.
     */
    private int getVersionFromTag(String s) {
        for (int i = 0; i < s.length(); i++) {
            for (char c : ignoredChars) {
                if (s.charAt(i) == c) {
                    s = s.replace(String.valueOf(c), "");
                }
            }
        }

        return Integer.parseInt(s);
    }
}
