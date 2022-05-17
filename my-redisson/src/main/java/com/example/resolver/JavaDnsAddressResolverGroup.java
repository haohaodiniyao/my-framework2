package com.example.resolver;

import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.AddressResolver;
import io.netty.resolver.AddressResolverGroup;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.internal.UnstableApi;

import java.net.InetSocketAddress;

@UnstableApi
public class JavaDnsAddressResolverGroup extends AddressResolverGroup<InetSocketAddress> {

    public JavaDnsAddressResolverGroup(
            Class<? extends DatagramChannel> channelType,
            DnsServerAddressStreamProvider nameServerProvider) {
    }

    public AddressResolver<InetSocketAddress> getResolver(final EventExecutor executor) {
        return new JavaInetAddressResolver(executor, null).asAddressResolver();
    }

    @Override
    protected AddressResolver<InetSocketAddress> newResolver(EventExecutor executor) throws Exception {
        return new JavaInetAddressResolver(executor, null).asAddressResolver();
    }

}