/**
 *
 * Copyright 2016 Xiaofei
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package xiaofei.library.shellyutils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Response;
import xiaofei.library.shelly.Domino;
import xiaofei.library.shelly.TaskDomino;
import xiaofei.library.shelly.function.Action0;
import xiaofei.library.shelly.function.Action1;
import xiaofei.library.shelly.function.Action2;
import xiaofei.library.shelly.function.Function1;
import xiaofei.library.shelly.function.TargetAction0;
import xiaofei.library.shelly.function.TargetAction1;
import xiaofei.library.shelly.function.TargetAction2;
import xiaofei.library.shelly.scheduler.Scheduler;
import xiaofei.library.shelly.util.Player;
import xiaofei.library.shelly.util.Triple;

/**
 * Created by Xiaofei on 16/6/28.
 */
public class RetrofitDomino2<T, R> extends TaskDomino<T, InputResponseWrapper<T, R>, Throwable> {

    protected RetrofitDomino2(Object label, Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> player) {
        super(label, player);
    }

    private RetrofitDomino2(TaskDomino<T, InputResponseWrapper<T, R>, Throwable> domino) {
        //can use getPlayer(), but not here!
        this(domino.getLabel(), domino.getPlayer());
    }
    @Override
    public RetrofitDomino2<T, R> uiThread() {
        return new RetrofitDomino2<T, R>(super.uiThread());
    }

    @Override
    public RetrofitDomino2<T, R> background() {
        return new RetrofitDomino2<T, R>(super.background());
    }

    @Override
    public RetrofitDomino2<T, R> backgroundQueue() {
        return new RetrofitDomino2<T, R>(super.backgroundQueue());
    }

    public Domino<T, R> endTaskResult() {
        return endTask().filter(new Function1<InputResponseWrapper<T, R>, Boolean>() {
            @Override
            public Boolean call(InputResponseWrapper<T, R> input) {
                return input.response.isSuccessful();
            }
        }).map(new Function1<InputResponseWrapper<T, R>, R>() {
            @Override
            public R call(InputResponseWrapper<T, R> input) {
                return input.response.body();
            }
        });
    }

    @Override
    public RetrofitDomino2<T, R> finallyDo(Action0 action0) {
        return new RetrofitDomino2<T, R>(super.finallyDo(action0));
    }

    @Override
    public <S> RetrofitDomino2<T, R> finallyDo(Class<? extends S> clazz, TargetAction0<? super S> targetAction0) {
        return new RetrofitDomino2<T, R>(super.finallyDo(clazz, targetAction0));
    }

    @Override
    public RetrofitDomino2<T, R> onFailure(Action0 action0) {
        return new RetrofitDomino2<T, R>(super.onFailure(action0));
    }

    @Override
    public RetrofitDomino2<T, R> onFailure(Action1<? super Throwable> action1) {
        return new RetrofitDomino2<T, R>(super.onFailure(action1));
    }

    @Override
    public <S> RetrofitDomino2<T, R> onFailure(Class<? extends S> clazz, TargetAction0<? super S> targetAction0) {
        return new RetrofitDomino2<T, R>(super.onFailure(clazz, targetAction0));
    }

    @Override
    public <S> RetrofitDomino2<T, R> onFailure(Class<? extends S> clazz, TargetAction1<? super S, ? super Throwable> targetAction1) {
        return new RetrofitDomino2<T, R>(super.onFailure(clazz, targetAction1));
    }

    @Override
    public RetrofitDomino2<T, R> onFailure(Domino<Throwable, ?> domino) {
        return new RetrofitDomino2<T, R>(super.onFailure(domino));
    }

    @Deprecated
    @Override
    public RetrofitDomino2<T, R> onSuccess(Action0 action0) {
        return new RetrofitDomino2<T, R>(super.onSuccess(action0));
    }

    @Deprecated
    @Override
    public RetrofitDomino2<T, R> onSuccess(Action1<? super InputResponseWrapper<T, R>> action1) {
        return new RetrofitDomino2<T, R>(super.onSuccess(action1));
    }

    @Deprecated
    @Override
    public <S> RetrofitDomino2<T, R> onSuccess(Class<? extends S> clazz, TargetAction0<? super S> targetAction0) {
        return new RetrofitDomino2<T, R>(super.onSuccess(clazz, targetAction0));
    }

    @Override
    public <S> RetrofitDomino2<T, R> onSuccess(Class<? extends S> clazz, TargetAction1<? super S, ? super InputResponseWrapper<T, R>> targetAction1) {
        return new RetrofitDomino2<T, R>(super.onSuccess(clazz, targetAction1));
    }

    @Deprecated
    @Override
    public RetrofitDomino2<T, R> onSuccess(Domino<InputResponseWrapper<T, R>, ?> domino) {
        return new RetrofitDomino2<T, R>(super.onSuccess(domino));
    }

    private static <T, R> boolean responseSuccess(Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple) {
        return triple.first && triple.second.response.isSuccessful() && triple.second.response.body() != null;
    }

    public RetrofitDomino2<T, R> onResult(final Action0 action0) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                boolean hasResult = false;
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseSuccess(triple)) {
                                        hasResult = true;
                                        break;
                                    }
                                }
                                if (hasResult) {
                                    action0.call();
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public RetrofitDomino2<T, R> onResult(final Action2<T, R> action2) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseSuccess(triple)) {
                                        action2.call(triple.second.input, triple.second.response.body());
                                    }
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public <S> RetrofitDomino2<T, R> onResult(final Class<? extends S> clazz, final TargetAction0<? super S> targetAction0) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                boolean hasResult = false;
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseSuccess(triple)) {
                                        hasResult = true;
                                        break;
                                    }
                                }
                                if (hasResult) {
                                    CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                    for (Object object : objects) {
                                        targetAction0.call(clazz.cast(object));
                                    }
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public <S> RetrofitDomino2<T, R> onResult(final Class<? extends S> clazz, final TargetAction2<? super S, T, R> targetAction2) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                for (Object object : objects) {
                                    for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                        if (responseSuccess(triple)) {
                                            targetAction2.call(clazz.cast(object), triple.second.input, triple.second.response.body());
                                        }
                                    }
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public RetrofitDomino2<T, R> onResult(final Domino<InputResponseWrapper<T, R>, ?> domino) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                ArrayList<InputResponseWrapper<T, R>> newInput = new ArrayList<InputResponseWrapper<T, R>>();
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseSuccess(triple)) {
                                        newInput.add(triple.second);
                                    }
                                }
                                if (!newInput.isEmpty()) {
                                    domino.getPlayer().call(newInput);
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    private static <T, R> boolean responseFailure(Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple) {
        return triple.first && (
                triple.second.response.isSuccessful() && triple.second.response.body() == null ||
                        !triple.second.response.isSuccessful());
    }

    public RetrofitDomino2<T, R> onResponseFailure(final Action0 action0) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                boolean failure = false;
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseFailure(triple)) {
                                        failure = true;
                                        break;
                                    }
                                }
                                if (failure) {
                                    action0.call();
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public RetrofitDomino2<T, R> onResponseFailure(final Action1<Response<R>> action1) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseFailure(triple)) {
                                        action1.call(triple.second.response);
                                    }
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public <S> RetrofitDomino2<T, R> onResponseFailure(final Class<? extends S> clazz, final TargetAction0<? super S> targetAction0) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                boolean failure = false;
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseFailure(triple)) {
                                        failure = true;
                                        break;
                                    }
                                }
                                if (failure) {
                                    CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                    for (Object object : objects) {
                                        targetAction0.call(clazz.cast(object));
                                    }
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public <S> RetrofitDomino2<T, R> onResponseFailure(final Class<? extends S> clazz, final TargetAction1<? super S, Response<R>> targetAction1) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                CopyOnWriteArrayList<Object> objects = TARGET_CENTER.getObjects(clazz);
                                for (Object object : objects) {
                                    for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                        if (responseFailure(triple)) {
                                            targetAction1.call(clazz.cast(object), triple.second.response);
                                        }
                                    }
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }

    public RetrofitDomino2<T, R> onResponseFailure(final Domino<Response<R>, ?> domino) {
        return new RetrofitDomino2<T, R>(getLabel(),
                new Player<T, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                    @Override
                    public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<T> input) {
                        final Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> scheduler = getPlayer().call(input);
                        scheduler.play(new Player<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>, Triple<Boolean, InputResponseWrapper<T, R>, Throwable>>() {
                            @Override
                            public Scheduler<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> call(List<Triple<Boolean, InputResponseWrapper<T, R>, Throwable>> input) {
                                ArrayList<Response<R>> newInput = new ArrayList<Response<R>>();
                                for (Triple<Boolean, InputResponseWrapper<T, R>, Throwable> triple : input) {
                                    if (responseFailure(triple)) {
                                        newInput.add(triple.second.response);
                                    }
                                }
                                if (!newInput.isEmpty()) {
                                    domino.getPlayer().call(newInput);
                                }
                                return scheduler;
                            }
                        });
                        return scheduler;
                    }
                });
    }
}
