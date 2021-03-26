import React, {useState} from 'react';
import {Link, useHistory} from 'react-router-dom';
import './Navbar.css';
import img1 from '../../img/profile-picture-default.jpeg';
import SearchBar from '../SearchBar/SearchBar';
import { useDispatch, useSelector } from 'react-redux';

//import Close from '@material-ui/icons/Close';
//import Menu from "@material-ui/icons/Menu";

export const Navbar: React.FC<any> = () => {

    const history = useHistory();

    const state = useSelector<any, any>((state) => state);

    const onLogOut = () => {

        localStorage.clear();
        history.push(`/`);
    };

    return (
        <div>
            <nav className='navbar'>
                <Link className='navbar-logo' to={`/user/${state.user.user.username}`} ><img className="profile-pic-icon" src={img1} alt="Default profile picture" /></Link>
                <ul className="nav-menu">
                    <li className='nav-item'><Link to="/home" className='nav-links'>Feed</Link></li>
                    <li className='nav-item' ><SearchBar /></li>
                    <li className='nav-item-button'><Link to="/"><button className="logout-button" onClick={onLogOut}>Logout</button></Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar;
