/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.plugin.ssh.key.utils;

import java.net.URI;
import java.util.regex.Pattern;

/** @author Anatoliy Bazko */
public class UrlUtils {

  public static final Pattern GIT_URL_PATTERN =
      Pattern.compile("([^\\\\/@:]+@[^\\\\/@:]+)(:|/)[^\\\\@:]+");

  private UrlUtils() {}

  /**
   * Parses URL and get host from it, if it is possible
   *
   * @param url url to the repository
   * @return host if it exists in URL or <code>null</code> if it doesn't.
   */
  public static String getHost(String url) {
    if (url.contains("://")) {
      /*
         Host between ("://" or "@") and (":" or "/")
         for ssh or git Schema uri.
         ssh://user@host.com/some/path
         ssh://host.com/some/path
         git://host.com/user/repo
         can be with port
         ssh://host.com:port/some/path
      */
      URI uri = URI.create(url);
      return uri.getHost();
    } else if (GIT_URL_PATTERN.matcher(url).matches()) {
      /*
         Host between "@" and ":"
         user@host.com:login/repo
      */
      return url.substring(url.indexOf("@") + 1, url.indexOf(":"));
    } else {
      return null;
    }
  }
}
