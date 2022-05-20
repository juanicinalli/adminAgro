import { ILocalidad } from 'app/shared/model/localidad.model';
import { IPais } from 'app/shared/model/pais.model';

export interface IProvincia {
  id?: number;
  nombre?: string | null;
  localidads?: ILocalidad[] | null;
  pais?: IPais | null;
}

export const defaultValue: Readonly<IProvincia> = {};
