package com.esisba.tp1.projection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import com.esisba.tp1.entity.Server;

@Projection(name="server_projection", types=Server.class)
public interface ServerProjection {
    public Long getIdServer(); 

    @Value("#{target.serverDetail.name}")    
    public String getName();

    @Value("#{target.configuration.cpu}")
    public String getCpu();   

    @Value("#{target.configuration.ram}")
    public String getRam(); 

    @Value("#{target.data_center}")
    public String getData_center();   
}

