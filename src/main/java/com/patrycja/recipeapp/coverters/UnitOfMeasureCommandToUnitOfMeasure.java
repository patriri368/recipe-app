package com.patrycja.recipeapp.coverters;

import com.patrycja.recipeapp.commands.RecipeCommand;
import com.patrycja.recipeapp.commands.UnitOfMeasureCommand;
import com.patrycja.recipeapp.domain.Category;
import com.patrycja.recipeapp.domain.Recipe;
import com.patrycja.recipeapp.domain.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {
        if (source == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
    }
}