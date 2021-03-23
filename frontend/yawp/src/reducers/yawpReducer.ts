import {ADD_YAWP,FETCH_YAWPS} from '../actions/types'



const initialState = {
    items:[],
    item:{}
}
export type Action={type:string; payload:string};
export const yawpReducer=(state=initialState, action:Action) =>{

    switch(action.type) {
        case ADD_YAWP:
            return {
                ...state,
                item: action.payload
            }
        case FETCH_YAWPS:
            return {
                ...state,
                items: action.payload
            }
        default:
            return state;
    }
}
