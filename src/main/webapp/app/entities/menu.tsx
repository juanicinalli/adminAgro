import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/pais">
        <Translate contentKey="global.menu.entities.pais" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/provincia">
        <Translate contentKey="global.menu.entities.provincia" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/localidad">
        <Translate contentKey="global.menu.entities.localidad" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/empleado">
        <Translate contentKey="global.menu.entities.empleado" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/trabajo">
        <Translate contentKey="global.menu.entities.trabajo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/tarea">
        <Translate contentKey="global.menu.entities.tarea" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/campo">
        <Translate contentKey="global.menu.entities.campo" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/insumos">
        <Translate contentKey="global.menu.entities.insumos" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/manejo">
        <Translate contentKey="global.menu.entities.manejo" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu as React.ComponentType<any>;
