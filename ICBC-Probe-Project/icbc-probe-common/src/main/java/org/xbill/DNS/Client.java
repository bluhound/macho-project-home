// Copyright (c) 2005 Brian Wellington (bwelling@xbill.org)

package org.xbill.DNS;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

import org.xbill.DNS.utils.hexdump;

class Client {

  protected long endTime;
  protected SelectionKey key;

  protected Client(SelectableChannel channel, long endTime) throws IOException {
    boolean done = false;
    Selector selector = null;
    this.endTime = endTime;
    try {
      selector = Selector.open();
      channel.configureBlocking(false);
      key = channel.register(selector, SelectionKey.OP_READ);
      done = true;
    } finally {
      if (!done && selector != null)
        selector.close();
      if (!done)
        channel.close();
    }
  }

  static protected void blockUntil(SelectionKey key, long endTime) throws IOException {
    long timeout = endTime - System.currentTimeMillis();
    int nkeys = 0;
    if (timeout > 0)
      nkeys = key.selector().select(timeout);
    else if (timeout == 0)
      nkeys = key.selector().selectNow();
    //System.err.println("nkeys=" + nkeys);
    if (nkeys == 0)
      throw new SocketTimeoutException();
  }

  static protected void verboseLog(String prefix, byte[] data) {
    if (Options.check("verbosemsg"))
      System.err.println(hexdump.dump(prefix, data));
  }

  void cleanup() throws IOException {
    key.selector().close();
    key.channel().close();
  }

}
