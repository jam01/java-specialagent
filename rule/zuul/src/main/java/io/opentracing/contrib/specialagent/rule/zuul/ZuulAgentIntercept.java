/* Copyright 2019 The OpenTracing Authors
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

package io.opentracing.contrib.specialagent.rule.zuul;

import java.util.List;

import com.netflix.zuul.ZuulFilter;

import io.opentracing.util.GlobalTracer;

public class ZuulAgentIntercept {
  @SuppressWarnings("unchecked")
  public static Object exit(final Object returned, final Object arg) {
    final List<ZuulFilter> filters = (List<ZuulFilter>)returned;
    if (arg.equals(TracePreZuulFilter.TYPE)) {
      for (final ZuulFilter filter : filters)
        if (filter instanceof TracePreZuulFilter)
          return returned;

      filters.add(new TracePreZuulFilter(GlobalTracer.get()));
    }
    else if (arg.equals(TracePostZuulFilter.TYPE)) {
      for (final ZuulFilter filter : filters)
        if (filter instanceof TracePostZuulFilter)
          return returned;

      filters.add(new TracePostZuulFilter());
    }

    return returned;
  }
}