package com.morpho.demo.tools;

import java.util.Date;

/**
 * Created by Alejandro Ruiz on 12/10/2015.
 */
public class PDFScanTool {

    public static String ObtenerNumeroIdentificacion(String tipo,String cadena)
    {
        String numeroIdentificacion="";
        if(tipo.equals("01"))
        {
            numeroIdentificacion=cadena.substring(38,48);
        }
        else if(tipo.equals("02"))
        {
            numeroIdentificacion=cadena.substring(48,58);
        }
        else if(tipo.equals("03"))
        {
            numeroIdentificacion=cadena.substring(48,58);
        }
        else if(tipo.equals("05"))
        {
            numeroIdentificacion=cadena.substring(17,27);
        }

        try
        {
            numeroIdentificacion= String.valueOf(Integer.valueOf(numeroIdentificacion));
        }
        catch(Exception ex)
        {
            numeroIdentificacion="";
        }

        return numeroIdentificacion;
    }


    public static String ObtenerTipo(String cadena)
    {
        String tipo=cadena.substring(0, 2);
        if(tipo.equals("01") || tipo.equals("02") || tipo.equals("03"))
        {
            return tipo;
        }
        else
        {
            tipo=cadena.substring(6, 8);
            if(tipo.equals("05"))
                return tipo;

        }

        return "";
    }


    public static String ObtenerCadena(String cadena)
    {
        boolean b=true;
        cadena=cadena.replaceAll("[\u0000-\u001f]"," ");
        while(b)
        {
            if(cadena.substring(0,1).equals(" "))
            {
                cadena=cadena.substring(1,cadena.length());
            }
            else
                b=false;
        }
        return cadena;
    }


    public static String ObtenerNombres(String tipo,String cadena)
    {
        String nombres="";
        if(tipo.equals("01"))
        {
            nombres=cadena.substring(94,140);
        }
        else if(tipo.equals("02"))
        {
            nombres=cadena.substring(104,150);
        }
        else if(tipo.equals("03"))
        {
            nombres=cadena.substring(104,150);
        }
        else if(tipo.equals("05"))
        {
            nombres=cadena.substring(83,139);
        }

        return nombres;
    }

    public static String QuitarEspacios(String cadena)
    {
        boolean b= true;
        while(b)
        {
            if (cadena.contains("  "))
            {
                cadena=cadena.replace("  ", " ");
            }
            else
                b=false;
        }
        return cadena;
    }

    public static String ObtenerApellidos(String tipo,String cadena)
    {
        String apellidos="";
        if(tipo.equals("01"))
        {
            apellidos=cadena.substring(48,94);
        }
        else if(tipo.equals("02"))
        {
            apellidos=cadena.substring(58,104);
        }
        else if(tipo.equals("03"))
        {
            apellidos=cadena.substring(58,104);
        }
        else if(tipo.equals("05"))
        {
            apellidos=cadena.substring(27,83);
        }


        return apellidos;
    }


    public static String ObtenerSexo(String tipo,String cadena)
    {
        String sexo="";
        if(tipo.equals("01"))
        {
            sexo=cadena.substring(141,142);
        }
        else if(tipo.equals("02"))
        {
            sexo=cadena.substring(151,152);
        }
        else if(tipo.equals("03"))
        {
            sexo=cadena.substring(151,152);
        }
        else if(tipo.equals("05"))
        {
            sexo=cadena.substring(139,140);
        }


        return sexo;
    }

    public static String ObtenerFechaNacimiento(String tipo,String cadena)
    {
        String fechaNacimiento="";
        if(tipo.equals("01"))
        {
            fechaNacimiento=cadena.substring(142,150);
        }
        else if(tipo.equals("02"))
        {
            fechaNacimiento=cadena.substring(152,160);
        }
        else if(tipo.equals("03"))
        {
            fechaNacimiento=cadena.substring(152,160);
        }
        else if(tipo.equals("05"))
        {
            fechaNacimiento=cadena.substring(140,148);
        }

        try
        {
            long fechaValida= Date.parse(fechaNacimiento.substring(0, 4) + "/" + fechaNacimiento.substring(4, 6) + "/" + fechaNacimiento.substring(6, 8));
        }
        catch(Exception ex)
        {
            return "";
        }

        return fechaNacimiento.substring(0,4)+"/"+fechaNacimiento.substring(4,6)+"/"+fechaNacimiento.substring(6,8);
    }
}
