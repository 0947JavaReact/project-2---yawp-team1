import { CallToActionSharp } from '@material-ui/icons';
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
    items: []
}
export type Action = { type: string; payload: string };
export const yawpReducer = (state = initialState, action: Action) => {
    switch (action.type) {
        case ADD_YAWP:
            return {
                ...state,
                items: [action.payload, ...state.items]
            };

        case FETCH_FOLLOWING_YAWPS:
            return {
                ...state,
                items: action.payload
            };
        case FETCH_USER_YAWPS:
            return{
                ...state,
                items: action.payload
            }

        default:
            return state;
    }
}
