package br.com.personal.commonlibutils.manager.mapperbeans.interfaces;

import org.mapstruct.InheritInverseConfiguration;

import java.util.Collection;
import java.util.List;

public interface AbstractMapper<T,S> {

    T to(S source);
    List<T> to(Collection<S> source);

    @InheritInverseConfiguration
    S toS(T source);
    List<S> toS(Collection<T> source);


}
