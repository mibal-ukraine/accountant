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

import ua.mibal.accountant.model.Account;
import ua.mibal.accountant.model.Commit;
import ua.mibal.accountant.model.sctructure.DynaCommitArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author Michael Balakhon
 * @link http://t.me/mibal_ua
 */
public class TXTDataParser implements DataParser {

    @Override
    public Commit[] getCommits(final Account account) throws IOException {

        File myObj = new File(account.getPATH());
        Scanner scanner = new Scanner(myObj);

        DynaCommitArray commits = new DynaCommitArray();
        String line;
        String time = null;
        String name = null;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (isNumber(line.charAt(0))) {
                StringBuilder str = new StringBuilder();
                for (int j = 0; j < line.length(); j++) {
                    char ch = line.charAt(j);
                    if (ch != '|') {
                        str.append(ch);
                    } else {
                        time = str.toString().trim();
                        str = new StringBuilder();
                    }
                }
                name = str.toString().trim();
            } else {
                commits.add(new Commit(time, name, line));
            }
        }
        scanner.close();
        return commits.asArray();
    }

    private boolean isNumber(final char ch) {
        return '1' <= ch && ch <= '9';
    }

    @Override
    public void add(final Account account, final Commit commitToAdd) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Commit[] commits = getCommits(account);
        if (commits != null) {
            for (final Commit commit : commits) {
                stringBuilder.append(commit);
            }
            stringBuilder.append(commitToAdd.toString());


            File file = new File(account.getPATH());
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(stringBuilder.toString());
            myWriter.close();
        }
    }
}
