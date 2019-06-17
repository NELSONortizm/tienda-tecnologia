
package dominio;

import dominio.repositorio.RepositorioProducto;
import dominio.GarantiaExtendida;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dominio.repositorio.RepositorioGarantiaExtendida;

public class Vendedor {

    public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";

    private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;

    public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioGarantia = repositorioGarantia;

    }

    public void generarGarantia(Producto producto, GarantiaExtendida Ge) throws Exception
    {
    	String fechap = Ge.getFechaSolicitudGarantia().toString() ;//convierto la fecha a String
    	//double garantia_extendida;
        int contador = 0;
         //1. contar las vocales del codigo
        for (int x = 0; x < producto.getCodigo().length(); x++)
        {
            if ((producto.getCodigo().charAt(x) == 'a') || (producto.getCodigo().charAt(x) == 'e') ||
                      (producto.getCodigo().charAt(x) == 'i') || (producto.getCodigo().charAt(x) == 'o') || 
                      (producto.getCodigo().charAt(x) == 'u') || (producto.getCodigo().charAt(x) == 'A') ||
                      (producto.getCodigo().charAt(x) == 'E') || (producto.getCodigo().charAt(x) == 'I') ||
                      (producto.getCodigo().charAt(x) == 'O') || (producto.getCodigo().charAt(x) == 'U')) 
            {
                contador++;
            }//end if que calcula cuantas vocales tiene el codigo delproducto
         }//end for       
            if (contador > 3)//si el codigo tiene mas de tres vocales no tiene garantia
                {
                    System.out.println("Este producto no cuenta con\n" +
                    " garantía extendida” y no se debe generar la garantía");
                    this.tieneGarantia("no");
                }
                else// si el codigo de producto tiene menos de tres vocales se calcula garantia y dias
                    //de garantia extendida
                {
                    if (producto.getPrecio() >500000)
                    {
                    	this.tieneGarantia("si");
                        Ge.setPrecioGarantia(producto.getPrecio()*0.20);
                        
                        System.out.println("Precio producto: "+ producto.getPrecio()+
                        		"Garantia extendida del producto  "+Ge.getPrecioGarantia());
                        
                       //calcula la fecha de inicio y termino de la garantia
                       Ge.setFechaFinGarantia( new SimpleDateFormat("dd-MM-yyyy").parse(calcular_dias_garantia_precionayor500(fechap)));
                       
                    }
                    else
                    {
                    	this.tieneGarantia("si");
                        Ge.setPrecioGarantia(producto.getPrecio()*0.10);
                        System.out.println("Precio producto: "+  producto.getPrecio()+
                        		"Garantia extendida del producto  "+ Ge.getPrecioGarantia());
                        //se calcula la fecha de inicio y termino de la garantia
                        
                       // calcular_dias_garantia_preciomenor500(fechap); 
                        Ge.setFechaFinGarantia( new SimpleDateFormat("dd-MM-yyyy").parse(calcular_dias_garantia_preciomenor500(fechap))); 
                        
                        
                    }//end if
                        
                }//end if contador
   	
  
    	
        throw new UnsupportedOperationException("Método pendiente por implementar");

    }

    public boolean tieneGarantia(String codigo) {
    	
    	if (codigo.equals("si"))
    	{
    		return true;
    	}else
    	{
    		 return false;
    	}
       
    }
    
    //
    public String calcular_dias_garantia_preciomenor500(String fechap) 
    {
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
         LocalDate fechaInicio = LocalDate.parse(fechap, formateador);//recibo el 
                                                  //parametro fecha y le doy formato
         LocalDate fechaFin;//defino la variable fechaFin 
              
        fechaFin= fechaInicio.plusDays(100);
        System.out.println("La fecha de expiración garantia es: "+fechaFin);
        return fechaFin.toString();  
       
    
    }//end calcular_dias_garantia_preciomenor500
    
    public  String calcular_dias_garantia_precionayor500(String fechap)
    {
       
         DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
         // Lo convertimos a objeto para poder trabajar con él
         LocalDate fechaInicio = LocalDate.parse(fechap, formateador);
         LocalDate fechaFin;
         LocalDate totalDiasGarantia;
        
        // System.out.println(fechaInicio.getDayOfWeek());
          
        if (fechaInicio.getDayOfWeek().toString()=="MONDAY")//SI EL DIA DE EXPEDICION ES LUNES 
                                                            //SE CUENTA DESDE EL MARTES
        {
            fechaFin= fechaInicio.plusDays(201);//SUMO 201 dias porque es lunes
            System.out.println("El día de hoy es lunes la fecha de garantía comienza el próximo martes: "
                      +fechaInicio.plusDays(1)+ "Finaliza el:"+fechaFin);
            
            
            //si la fecha_expiracion garantia es domingo se pasa para el siguiente día habil
             //en este caso sería para el martes porque no se cuentan los lunes y como se inicia
             //un día lunes se suman tres dias mas 
            if (fechaFin.getDayOfWeek().toString()=="SUNDAY")
            {
                fechaFin= fechaInicio.plusDays(203);                
                
            }
            else
            {
              
            }//end if (fechaFin.getDayOfWeek().toString()=="SUNDAY")
            
           
        }//Si la fecha actual no es lunes
        else
        {
          
            fechaFin= fechaInicio.plusDays(200);
          
             //si la fecha_expiracion garantia es domingo se pasa para el siguiente día habil
             //en este caso sería para el martes porque no se cuentan los lunes
            if (fechaFin.getDayOfWeek().toString()=="SUNDAY")
            {
                fechaFin= fechaInicio.plusDays(202);
            }
        }
       
        return fechaFin.toString();
        
    }//end calcular_dias_garantia_precionayor500

}
