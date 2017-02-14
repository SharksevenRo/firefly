package com.firefly.server.http2.router;

import com.firefly.server.http2.SimpleRequest;
import com.firefly.server.http2.router.impl.RouterManagerImpl;
import com.firefly.utils.function.Action1;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pengtao Qiu
 */
public interface RouterManager extends RequestAcceptor {

    class RouterMatchResult implements Comparable<RouterMatchResult> {

        private final Router router;
        private final Map<String, String> parameters;
        private final Set<Matcher.MatchType> matchTypes;

        public RouterMatchResult(Router router, Map<String, String> parameters, Set<Matcher.MatchType> matchTypes) {
            this.router = router;
            this.parameters = parameters;
            this.matchTypes = matchTypes;
        }

        public Router getRouter() {
            return router;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public Set<Matcher.MatchType> getMatchTypes() {
            return matchTypes;
        }

        @Override
        public int compareTo(RouterMatchResult o) {
            return router.compareTo(o.getRouter());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RouterMatchResult that = (RouterMatchResult) o;
            return Objects.equals(router, that.router);
        }

        @Override
        public int hashCode() {
            return Objects.hash(router);
        }
    }

    Router register();

    NavigableSet<RouterMatchResult> findRouter(String method, String path, String contentType, String accept);

    RouterManager routerNotFound(Action1<SimpleRequest> routerNotFound);

    static RouterManager create() {
        // TODO
        return new RouterManagerImpl();
    }

    static RouterManager createEmpty() {
        return new RouterManagerImpl();
    }
}