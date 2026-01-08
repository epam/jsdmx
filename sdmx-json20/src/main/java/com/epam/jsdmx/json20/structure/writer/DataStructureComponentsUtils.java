package com.epam.jsdmx.json20.structure.writer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.epam.jsdmx.infomodel.sdmx30.Component;
import com.epam.jsdmx.infomodel.sdmx30.ComponentList;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class DataStructureComponentsUtils {

    public static <T extends Component> boolean isEmpty(ComponentList<T> componentList) {
        if (componentList == null) {
            return true;
        }

        List<T> components = Optional.of(componentList)
            .map(ComponentList::getComponents)
            .orElse(Collections.emptyList());
        return components.isEmpty();
    }

    public static boolean areAllEmpty(ComponentList<?>... componentLists) {
        for (ComponentList<?> componentList : componentLists) {
            if (!isEmpty(componentList)) {
                return false;
            }
        }
        return true;
    }
}
