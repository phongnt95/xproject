import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUserX, defaultValue } from 'app/shared/model/user-x.model';

export const ACTION_TYPES = {
  FETCH_USERX_LIST: 'userX/FETCH_USERX_LIST',
  FETCH_USERX: 'userX/FETCH_USERX',
  CREATE_USERX: 'userX/CREATE_USERX',
  UPDATE_USERX: 'userX/UPDATE_USERX',
  DELETE_USERX: 'userX/DELETE_USERX',
  RESET: 'userX/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUserX>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type UserXState = Readonly<typeof initialState>;

// Reducer

export default (state: UserXState = initialState, action): UserXState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_USERX_LIST):
    case REQUEST(ACTION_TYPES.FETCH_USERX):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_USERX):
    case REQUEST(ACTION_TYPES.UPDATE_USERX):
    case REQUEST(ACTION_TYPES.DELETE_USERX):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_USERX_LIST):
    case FAILURE(ACTION_TYPES.FETCH_USERX):
    case FAILURE(ACTION_TYPES.CREATE_USERX):
    case FAILURE(ACTION_TYPES.UPDATE_USERX):
    case FAILURE(ACTION_TYPES.DELETE_USERX):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERX_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_USERX):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_USERX):
    case SUCCESS(ACTION_TYPES.UPDATE_USERX):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_USERX):
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

const apiUrl = 'api/user-xes';

// Actions

export const getEntities: ICrudGetAllAction<IUserX> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_USERX_LIST,
    payload: axios.get<IUserX>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IUserX> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_USERX,
    payload: axios.get<IUserX>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUserX> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_USERX,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUserX> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_USERX,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUserX> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_USERX,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
