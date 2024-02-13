package com.esisba.tp1.request;

public class CreateVmBody {
    public class Config {
        public String cpu;
        public String ram;
        public String disk;
    }
    
    public Long idUtilisateur;
    public Long idServeur;
    public Long idServeurBackup;
    public Config configuration;
}
