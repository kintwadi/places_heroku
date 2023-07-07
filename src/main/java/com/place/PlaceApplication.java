package com.place;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.place.admin.service.StorageManager;

import javax.annotation.Resource;



//@SpringBootApplication( exclude = { DataSourceAutoConfiguration.class } )
@SpringBootApplication
public class PlaceApplication
    implements CommandLineRunner
{

    @Resource
    StorageManager storageService;

    @Override
    public void run( String... arg )
        throws Exception
    {
        // storageService.deleteAll();
        storageService.init();
    }

    public static void main( String[] args )
    {
        SpringApplication.run( PlaceApplication.class,
                               args );
    }


}
