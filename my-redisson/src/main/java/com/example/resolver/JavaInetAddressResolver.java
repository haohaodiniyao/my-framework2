package com.example.resolver;

import io.netty.resolver.AddressResolver;
import io.netty.resolver.InetNameResolver;
import io.netty.resolver.NameResolver;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.internal.UnstableApi;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

@UnstableApi
public class JavaInetAddressResolver extends InetNameResolver {
    private  AddressResolver<InetSocketAddress> addressResolver;

    @Override
    public AddressResolver<InetSocketAddress> asAddressResolver() {
        AddressResolver<InetSocketAddress> result = addressResolver;
        if (result == null) {
            synchronized (this) {
                result = addressResolver;
                if (result == null) {
                    addressResolver = result = new JavaAddressResolver(executor(), this);
                }
            }
        }
        return result;
    }

    public JavaInetAddressResolver(EventExecutor executor, NameResolver<InetAddress> nameResolver) {
        super(executor);
    }

    @Override
    protected void doResolve(final String inetHost, final Promise<InetAddress> promise) throws Exception {
        try {
            promise.setSuccess(InetAddress.getByName(inetHost));
        } catch (Exception e) {
            promise.setFailure(new UnknownHostException(inetHost));
        }
    }

    @Override
    protected void doResolveAll(String inetHost, final Promise<List<InetAddress>> promise) throws Exception {
        try {
            promise.setSuccess(Arrays.asList(InetAddress.getAllByName(inetHost)));
        } catch (Exception e) {
            promise.setFailure(new UnknownHostException(inetHost));
        }
    }
}