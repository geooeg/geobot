package com.vgilab.geobot.util;

import com.vgilab.geobot.PhotoInfo;
import com.vgilab.geobot.persistence.entity.Coordinates;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;

/**
 *
 * @author ljzhang
 */
public class PhotoInfoToCoordinateMapper {
    
    /**
     *
     * @return
     */
    public static ModelMapper getEntityToDtoModellMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<Coordinates, PhotoInfo>() {
            @Override
            protected void configure() {
            }
        });
        return modelMapper;
    }
    
    /**
     *
     * @return
     */
    public static ModelMapper getDtoToEntityModellMapper() {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(new PropertyMap<PhotoInfo, Coordinates>() {
            @Override
            protected void configure() {
                
            }
        });
        return modelMapper;
    }
}
