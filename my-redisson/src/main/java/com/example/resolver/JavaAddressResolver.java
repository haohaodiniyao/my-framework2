package com.example.resolver;

import io.netty.resolver.AbstractAddressResolver;
import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.UnstableApi;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UnstableApi
public class JavaAddressResolver extends AbstractAddressResolver<InetSocketAddress> {

    final NameResolver<InetAddress> nameResolver;

    public JavaAddressResolver(EventExecutor executor, NameResolver<InetAddress> nameResolver) {
        super(executor, InetSocketAddress.class);
        this.nameResolver = nameResolver;
    }



    @Override
    protected boolean doIsResolved(InetSocketAddress address) {
        return !address.isUnresolved();
    }

    @Override
    protected void doResolve(final InetSocketAddress unresolvedAddress, final Promise<InetSocketAddress> promise)
            throws Exception {
        try {
            promise.setSuccess(new InetSocketAddress(InetAddress.getByName(unresolvedAddress.getHostName()), unresolvedAddress.getPort()));
        } catch (Exception e) {
            promise.setFailure(new UnknownHostException(unresolvedAddress.getHostName()));
        }
    }

    @Override
    protected void doResolveAll(final InetSocketAddress unresolvedAddress,
                                final Promise<List<InetSocketAddress>> promise) throws Exception {
        try {
            List<InetAddress> inetAddresses = Arrays.asList(InetAddress.getAllByName(unresolvedAddress.getHostName()));
            List<InetSocketAddress> socketAddresses =
                    new ArrayList<InetSocketAddress>(inetAddresses.size());
            for (InetAddress inetAddress : inetAddresses) {
                socketAddresses.add(new InetSocketAddress(inetAddress, unresolvedAddress.getPort()));
            }
            promise.setSuccess(socketAddresses);
        } catch (Exception e) {
            promise.setFailure(new UnknownHostException(unresolvedAddress.getHostName()));
        }
    }

    @Override
    public void close() {
        nameResolver.close();
    }
}