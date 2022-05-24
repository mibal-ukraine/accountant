/*
 * Copyright (c) 2022. http://t.me/mibal_ua
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ua.mibal.accountant.component;

import ua.mibal.accountant.model.Commit;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Michael Balakhon
 * @link http://t.me/mibal_ua
 */
public class Account {

    private final String name;

    private final String path;

    private final DataTXTParser dataTXTParser;

    private Commit[] lastCommits;

    public Account(final String name, DataPrinter dataPrinter) {
        this.name = name;
        path = "/Users/" + System.getProperty("user.name") + "/" + name + ".txt";
        dataTXTParser = new DataTXTParser(dataPrinter);
        lastCommits = null;
    }

    public final String getPATH() {
        return path;
    }

    public final String getName() {
        return name;
    }

    public void add(final Commit commitToAdd) throws IOException {
        dataTXTParser.add(this, commitToAdd);
        lastCommits = null;
    }

    public Commit[] getCommits() throws IOException{
        if (lastCommits == null) {
            lastCommits = dataTXTParser.getCommits(this).clone();
        }
        return lastCommits.clone();
    }
}
