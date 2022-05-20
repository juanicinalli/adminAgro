import { IProvincia } from 'app/shared/model/provincia.model';

export interface IPais {
  id?: number;
  nombre?: string | null;
  provincias?: IProvincia[] | null;
}

export const defaultValue: Readonly<IPais> = {};
