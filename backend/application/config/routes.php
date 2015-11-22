<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');
/*
| -------------------------------------------------------------------------
| URI ROUTING
| -------------------------------------------------------------------------
| This file lets you re-map URI requests to specific controller functions.
|
| Typically there is a one-to-one relationship between a URL string
| and its corresponding controller class/method. The segments in a
| URL normally follow this pattern:
|
|	example.com/class/method/id/
|
| In some instances, however, you may want to remap this relationship
| so that a different class/function is called than the one
| corresponding to the URL.
|
| Please see the user guide for complete details:
|
|	http://codeigniter.com/user_guide/general/routing.html
|
| -------------------------------------------------------------------------
| RESERVED ROUTES
| -------------------------------------------------------------------------
|
| There area two reserved routes:
|
|	$route['default_controller'] = 'welcome';
|
| This route indicates which controller class should be loaded if the
| URI contains no data. In the above example, the "welcome" class
| would be loaded.
|
|	$route['404_override'] = 'errors/page_missing';
|
| This route will tell the Router what URI segments to use if those provided
| in the URL cannot be matched to a valid route.
|
*/

$route['default_controller'] = "user";
$route['404_override'] = '';

$route['login'] = 'user/login';
$route['signup'] = 'user/signup';
$route['logout'] = 'user/logout';
$route['forgot-password'] = 'user/forgot_password';
$route['my-account'] = 'user/account';

/* Routing for Stripe Donation website */
//$route['pay/(:any)'] = 'pay/index/$1';

//Because we move from an older version of REST_Controller, route is still like: resource/id/123.
//Just use this to fit with existed api.
// New REST_Controller would be: resource/method/{parametter}
//$route['api/donation/id/(:any)'] = 'api/donation/index/$1'; --- donation site.



/*
 * RESTFul router implementation
 */
//res/id/function/id --> res/function/id/num/sid/num 
$route['([a-z_]+)/([a-z_]+)/(:any)'] = '$1/$2/$3'; 
$route['api/([a-z_]+)/(:any)/([a-z_]+)/(:any)'] = 'api/$1/$3/$2/$4'; 
//res/id/function --> res/function/id/num 
$route['api/([a-z_]+)/(:any)/([a-z_]+)'] = 'api/$1/$3/$2'; 
//res/function --> res/function 
$route['api/([a-z_]+)/([a-z_]+)'] = 'api/$1/$2/'; 
//res/id --> res/index/id/num 
$route['([a-z_]+)/(:any)'] = '$1/index/id/$2'; 
//res/ --> //res/ 
$route['api/([a-z_]+)'] = 'api/$1';


/* End of file routes.php */
/* Location: ./application/config/routes.php */
