import {ADD_USER,FETCH_USER,UPDATE_USER} from '../actions/types'

export interface User {
    username:string,
    id:number,
    password:string,
    bio:string,
    profile_pic:string,
    email:string,
    followers:User[],
    following:User[],
}

const initialState = {
    item:{}
}
export type Action={type:string; payload:string};
export const userReducer=(state=initialState, action:Action) =>{

    switch(action.type) {
        case ADD_USER:
            return {
                ...state,
                item: action.payload
            }
        case FETCH_USER:
            return {
                ...state,
                item: action.payload
            }
        case UPDATE_USER:
            return {
                ...state,
                item: action.payload
            }
        default:
            return state;
    }
}
