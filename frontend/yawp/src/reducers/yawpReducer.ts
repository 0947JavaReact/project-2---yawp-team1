import { ADD_YAWP, FETCH_YAWPS } from '../actions/types'

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
                item: action.payload
            };

        case FETCH_YAWPS:
            return {
                ...state,
                items: action.payload
            };

        default:
            return state;
    }
}
