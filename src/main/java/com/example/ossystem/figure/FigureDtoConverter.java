package com.example.ossystem.figure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import java.util.Optional;

public interface FigureDtoConverter {

    @Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public interface PetDtoConverter {

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "version", ignore = true)
        Figure toModel(FigureInputDTO dto);

        @Mapping(target = "version", ignore = true)
        Figure toModel(FigureInputDTO dto, Integer id);

        @Mapping(target = "id", ignore = true)
        @Mapping(target = "version", ignore = true)
        void update(@MappingTarget Figure pet, FigureInputDTO dto);

        default <T> T unpack(Optional<T> maybe){
            return maybe.orElse(null);
        }
    }
}
