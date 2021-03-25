import React, {useState} from 'react';
import {Link} from 'react-router-dom';
import './Navbar.css';
import img1 from '../../img/profile-picture-default.jpeg';
import SearchBar from '../SearchBar/SearchBar';

//import Close from '@material-ui/icons/Close';
//import Menu from "@material-ui/icons/Menu";

export const Navbar: React.FC<any> = () => {
    return (
        <div>
            <nav className='navbar'>
                <Link className='navbar-logo' to="/userpage" ><img className="profile-pic-icon" src={img1} alt="Default profile picture" /></Link>
                <ul className="nav-menu">
                    <li className='nav-item'><Link to="/feed" className='nav-links'>Feed</Link></li>
                    <li className='nav-item' ><SearchBar /></li>
                    <li className='nav-item-button'><Link to="/"><button className="logout-button">Logout</button></Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar;
