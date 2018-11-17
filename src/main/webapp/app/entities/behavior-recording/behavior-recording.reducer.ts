import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IBehaviorRecording, defaultValue } from 'app/shared/model/behavior-recording.model';

export const ACTION_TYPES = {
  FETCH_BEHAVIORRECORDING_LIST: 'behaviorRecording/FETCH_BEHAVIORRECORDING_LIST',
  FETCH_BEHAVIORRECORDING: 'behaviorRecording/FETCH_BEHAVIORRECORDING',
  CREATE_BEHAVIORRECORDING: 'behaviorRecording/CREATE_BEHAVIORRECORDING',
  UPDATE_BEHAVIORRECORDING: 'behaviorRecording/UPDATE_BEHAVIORRECORDING',
  DELETE_BEHAVIORRECORDING: 'behaviorRecording/DELETE_BEHAVIORRECORDING',
  RESET: 'behaviorRecording/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IBehaviorRecording>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type BehaviorRecordingState = Readonly<typeof initialState>;

// Reducer

export default (state: BehaviorRecordingState = initialState, action): BehaviorRecordingState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_BEHAVIORRECORDING_LIST):
    case REQUEST(ACTION_TYPES.FETCH_BEHAVIORRECORDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_BEHAVIORRECORDING):
    case REQUEST(ACTION_TYPES.UPDATE_BEHAVIORRECORDING):
    case REQUEST(ACTION_TYPES.DELETE_BEHAVIORRECORDING):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_BEHAVIORRECORDING_LIST):
    case FAILURE(ACTION_TYPES.FETCH_BEHAVIORRECORDING):
    case FAILURE(ACTION_TYPES.CREATE_BEHAVIORRECORDING):
    case FAILURE(ACTION_TYPES.UPDATE_BEHAVIORRECORDING):
    case FAILURE(ACTION_TYPES.DELETE_BEHAVIORRECORDING):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_BEHAVIORRECORDING_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_BEHAVIORRECORDING):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_BEHAVIORRECORDING):
    case SUCCESS(ACTION_TYPES.UPDATE_BEHAVIORRECORDING):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_BEHAVIORRECORDING):
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

const apiUrl = 'api/behavior-recordings';

// Actions

export const getEntities: ICrudGetAllAction<IBehaviorRecording> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_BEHAVIORRECORDING_LIST,
  payload: axios.get<IBehaviorRecording>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IBehaviorRecording> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_BEHAVIORRECORDING,
    payload: axios.get<IBehaviorRecording>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IBehaviorRecording> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_BEHAVIORRECORDING,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IBehaviorRecording> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_BEHAVIORRECORDING,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IBehaviorRecording> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_BEHAVIORRECORDING,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
