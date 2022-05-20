import pais from 'app/entities/pais/pais.reducer';
import provincia from 'app/entities/provincia/provincia.reducer';
import localidad from 'app/entities/localidad/localidad.reducer';
import empleado from 'app/entities/empleado/empleado.reducer';
import trabajo from 'app/entities/trabajo/trabajo.reducer';
import tarea from 'app/entities/tarea/tarea.reducer';
import campo from 'app/entities/campo/campo.reducer';
import insumos from 'app/entities/insumos/insumos.reducer';
import manejo from 'app/entities/manejo/manejo.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  pais,
  provincia,
  localidad,
  empleado,
  trabajo,
  tarea,
  campo,
  insumos,
  manejo,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
