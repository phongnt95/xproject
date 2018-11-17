import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IServiceOpt, defaultValue } from 'app/shared/model/service-opt.model';

export const ACTION_TYPES = {
  FETCH_SERVICEOPT_LIST: 'serviceOpt/FETCH_SERVICEOPT_LIST',
  FETCH_SERVICEOPT: 'serviceOpt/FETCH_SERVICEOPT',
  CREATE_SERVICEOPT: 'serviceOpt/CREATE_SERVICEOPT',
  UPDATE_SERVICEOPT: 'serviceOpt/UPDATE_SERVICEOPT',
  DELETE_SERVICEOPT: 'serviceOpt/DELETE_SERVICEOPT',
  RESET: 'serviceOpt/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IServiceOpt>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ServiceOptState = Readonly<typeof initialState>;

// Reducer

export default (state: ServiceOptState = initialState, action): ServiceOptState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SERVICEOPT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SERVICEOPT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SERVICEOPT):
    case REQUEST(ACTION_TYPES.UPDATE_SERVICEOPT):
    case REQUEST(ACTION_TYPES.DELETE_SERVICEOPT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SERVICEOPT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SERVICEOPT):
    case FAILURE(ACTION_TYPES.CREATE_SERVICEOPT):
    case FAILURE(ACTION_TYPES.UPDATE_SERVICEOPT):
    case FAILURE(ACTION_TYPES.DELETE_SERVICEOPT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEOPT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SERVICEOPT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SERVICEOPT):
    case SUCCESS(ACTION_TYPES.UPDATE_SERVICEOPT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SERVICEOPT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/service-opts';

// Actions

export const getEntities: ICrudGetAllAction<IServiceOpt> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SERVICEOPT_LIST,
  payload: axios.get<IServiceOpt>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IServiceOpt> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SERVICEOPT,
    payload: axios.get<IServiceOpt>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IServiceOpt> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SERVICEOPT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IServiceOpt> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SERVICEOPT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IServiceOpt> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SERVICEOPT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
