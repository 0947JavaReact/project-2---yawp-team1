import { isTemplateSpan } from 'typescript';
import { ADD_YAWP, FETCH_USER_YAWPS, FETCH_FOLLOWING_YAWPS } from '../actions/types'

export interface Yawp {
    id: number,
    username: string,
    content: string,
    likes: number,
    profilePic: string
}

const initialState = {
    items: [],
    item: {
        id: -1,
        username: "",
        content: "",
        likes: 0,
        profilePic: ""
    }
}
export type Action = { type: string; payload: string };
export const yawpReducer = (state = initialState, action: Action) => {
    switch (action.type) {
        case ADD_YAWP:
            return {
                ...state,
                items: [...state.items, action.payload]
            };

        case FETCH_FOLLOWING_YAWPS:
            return {
                ...state,
                items: action.payload
            };

        default:
            return state;
    }
}
