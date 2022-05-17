package com.example.resolver;

import io.netty.channel.socket.DatagramChannel;
import io.netty.resolver.dns.DnsAddressResolverGroup;
import io.netty.resolver.dns.DnsServerAddressStreamProvider;
import org.redisson.connection.AddressResolverGroupFactory;

public class JavaDnsAddressResolverGroupFactory implements AddressResolverGroupFactory {

    @Override
    public JavaDnsAddressResolverGroup create(Class<? extends DatagramChannel> channelType, DnsServerAddressStreamProvider nameServerProvider) {
        return new JavaDnsAddressResolverGroup(channelType, nameServerProvider);
    }

}