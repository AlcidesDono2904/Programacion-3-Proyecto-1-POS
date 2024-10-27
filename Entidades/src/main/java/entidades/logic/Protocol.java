package entidades.logic;

public class Protocol {
    public static final String SERVER="localhost";
    public static final int PORT=777;

    public static final int ERROR_NO_ERROR=0;
    public static final int ERROR=1;
    /*producot 1
    * categoria 2
    * cliente 3
    * cajero 4
    * linea 5
    * factura 6
    * */
    //producto
    public static final int PRODUCTO_CREATE=101;
    public static final int PRODUCTO_READ=102;
    public static final int PRODUCTO_UPDATE=103;
    public static final int PRODUCTO_DELETE=104;
    public static final int PRODUCTO_SEARCH=105;
    //categoria
    public static final int CATEGORIA_SEARCH=205;
    //cliente
    public static final int CLIENTE_CREATE=301;
    public static final int CLIENTE_READ=302;
    public static final int CLIENTE_UPDATE=303;
    public static final int CLIENTE_DELETE=304;
    public static final int CLIENTE_SEARCH=305;
    //cajero
    public static final int CAJERO_CREATE=401;
    public static final int CAJERO_READ=402;
    public static final int CAJERO_UPDATE=403;
    public static final int CAJERO_DELETE=404;
    public static final int CAJERO_SEARCH=405;
    //linea
    public static final int LINEA_SEARCH=505;//recibe una factura por parametro
    //factura
    public static final int FACTURA_CREATE=601;
    public static final int FACTURA_SEARCH=605;
    //aux
    public static final int IMPORTE_FACTURA=1001;
    public static final int RANGO_CATEGORIA=1002;
    public static final int LOGIN=1003;
    public static final int LOGOUT=1004;

}
