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

package io.opentracing.contrib.specialagent.rule.cassandra.driver3;

import com.datastax.driver.core.Session;

import io.opentracing.contrib.cassandra.TracingSession;
import io.opentracing.contrib.common.WrapperProxy;
import io.opentracing.util.GlobalTracer;

public class CassandraAgentIntercept {
  public static Object exit(final Object thiz) {
    return WrapperProxy.wrap(thiz, new TracingSession((Session)thiz, GlobalTracer.get()));
  }
}