/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.function.core;

import java.util.function.Function;

import org.springframework.util.ClassUtils;

/**
 * @author Dave Syer
 *
 */
public class IsolatedFunction<S, T> implements Function<S, T> {

	private final Function<S, T> function;

	public IsolatedFunction(Function<S, T> function) {
		this.function = function;
	}

	@Override
	public T apply(S item) {
		ClassLoader context = ClassUtils
				.overrideThreadContextClassLoader(function.getClass().getClassLoader());
		try {
			return function.apply(item);
		}
		finally {
			ClassUtils.overrideThreadContextClassLoader(context);
		}
	}

}
