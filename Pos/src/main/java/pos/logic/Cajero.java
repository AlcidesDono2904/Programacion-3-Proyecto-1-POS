package pos.logic;

import java.util.Objects;

public class Cajero {

        private String id;
        private String nombre;

        public Cajero(){
                this.id="";
                this.nombre="";
        }

        public Cajero(String id, String nombre) {
                this.id = id;
                this.nombre = nombre;
        }

        public String getId() {return id;}

        public String getNombre() {return nombre;}

        public void setId(String id) {this.id = id;}

        public void setNombre(String nombre) {this.nombre = nombre;}

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Cajero cajero = (Cajero) o;
                return Objects.equals(id, cajero.id) && Objects.equals(nombre, cajero.nombre);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, nombre);
        }

        @Override
        public String toString() {
                return id+" "+nombre;
        }
}
