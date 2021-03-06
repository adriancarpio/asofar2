/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.asofar.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "se_empresa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeEmpresa.findAll", query = "SELECT s FROM SeEmpresa s")
    , @NamedQuery(name = "SeEmpresa.findByIdEmpresa", query = "SELECT s FROM SeEmpresa s WHERE s.idEmpresa = :idEmpresa")
    , @NamedQuery(name = "SeEmpresa.findByNombreComercial", query = "SELECT s FROM SeEmpresa s WHERE s.nombreComercial = :nombreComercial")
    , @NamedQuery(name = "SeEmpresa.findByRuc", query = "SELECT s FROM SeEmpresa s WHERE s.ruc = :ruc")
    , @NamedQuery(name = "SeEmpresa.findByTelefono", query = "SELECT s FROM SeEmpresa s WHERE s.telefono = :telefono")
    , @NamedQuery(name = "SeEmpresa.findByDireccion", query = "SELECT s FROM SeEmpresa s WHERE s.direccion = :direccion")
    , @NamedQuery(name = "SeEmpresa.findByCorreo", query = "SELECT s FROM SeEmpresa s WHERE s.correo = :correo")
    , @NamedQuery(name = "SeEmpresa.findByUsuarioCreacion", query = "SELECT s FROM SeEmpresa s WHERE s.usuarioCreacion = :usuarioCreacion")
    , @NamedQuery(name = "SeEmpresa.findByFechaCreacion", query = "SELECT s FROM SeEmpresa s WHERE s.fechaCreacion = :fechaCreacion")
    , @NamedQuery(name = "SeEmpresa.findByUsuarioActualizacion", query = "SELECT s FROM SeEmpresa s WHERE s.usuarioActualizacion = :usuarioActualizacion")
    , @NamedQuery(name = "SeEmpresa.findByFechaActualizacion", query = "SELECT s FROM SeEmpresa s WHERE s.fechaActualizacion = :fechaActualizacion")
    , @NamedQuery(name = "SeEmpresa.findByEstado", query = "SELECT s FROM SeEmpresa s WHERE s.estado = :estado")})
public class SeEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_empresa")
    private Long idEmpresa;
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "correo")
    private String correo;
    @Column(name = "usuario_creacion")
    private String usuarioCreacion;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;
    @Column(name = "fecha_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaActualizacion;
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seEmpresa")
    private List<PrProductos> prProductosList;
    @OneToMany(mappedBy = "idEmpresa")
    private List<PrSubgrupos> prSubgruposList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seEmpresa")
    private List<SeSucursal> seSucursalList;
    @OneToMany(mappedBy = "idEmpresa")
    private List<PrGrupos> prGruposList;
    @OneToMany(mappedBy = "idEmpresa")
    private List<VeUnidadServicio> veUnidadServicioList;
    @OneToMany(mappedBy = "idEmpresa")
    private List<PrPrestaciones> prPrestacionesList;
    @OneToMany(mappedBy = "idEmpresa")
    private List<PrTipoMedidas> prTipoMedidasList;

    public SeEmpresa() {
    }

    public SeEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioActualizacion() {
        return usuarioActualizacion;
    }

    public void setUsuarioActualizacion(String usuarioActualizacion) {
        this.usuarioActualizacion = usuarioActualizacion;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<PrProductos> getPrProductosList() {
        return prProductosList;
    }

    public void setPrProductosList(List<PrProductos> prProductosList) {
        this.prProductosList = prProductosList;
    }

    @XmlTransient
    public List<PrSubgrupos> getPrSubgruposList() {
        return prSubgruposList;
    }

    public void setPrSubgruposList(List<PrSubgrupos> prSubgruposList) {
        this.prSubgruposList = prSubgruposList;
    }

    @XmlTransient
    public List<SeSucursal> getSeSucursalList() {
        return seSucursalList;
    }

    public void setSeSucursalList(List<SeSucursal> seSucursalList) {
        this.seSucursalList = seSucursalList;
    }

    @XmlTransient
    public List<PrGrupos> getPrGruposList() {
        return prGruposList;
    }

    public void setPrGruposList(List<PrGrupos> prGruposList) {
        this.prGruposList = prGruposList;
    }

    @XmlTransient
    public List<VeUnidadServicio> getVeUnidadServicioList() {
        return veUnidadServicioList;
    }

    public void setVeUnidadServicioList(List<VeUnidadServicio> veUnidadServicioList) {
        this.veUnidadServicioList = veUnidadServicioList;
    }

    @XmlTransient
    public List<PrPrestaciones> getPrPrestacionesList() {
        return prPrestacionesList;
    }

    public void setPrPrestacionesList(List<PrPrestaciones> prPrestacionesList) {
        this.prPrestacionesList = prPrestacionesList;
    }

    @XmlTransient
    public List<PrTipoMedidas> getPrTipoMedidasList() {
        return prTipoMedidasList;
    }

    public void setPrTipoMedidasList(List<PrTipoMedidas> prTipoMedidasList) {
        this.prTipoMedidasList = prTipoMedidasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEmpresa != null ? idEmpresa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeEmpresa)) {
            return false;
        }
        SeEmpresa other = (SeEmpresa) object;
        if ((this.idEmpresa == null && other.idEmpresa != null) || (this.idEmpresa != null && !this.idEmpresa.equals(other.idEmpresa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.asofar.dto.SeEmpresa[ idEmpresa=" + idEmpresa + " ]";
    }
    
}
