package org.foi.nwtis.antbaric.components;

import java.util.List;

/**
 *
 * @author javert
 */
public class GResult {
	List<GAddressComponent> address_components;
	String formatted_address;
	GGeometry geometry;
	Boolean partial_match;
	String place_id;
	List<String> types;
}
class GGeometry {
	GViewport bounds;
	GCoordinates location;
	String location_type;
	GViewport viewport;
}
class GViewport {
	GCoordinates northeast;
	GCoordinates southwest;
}
class GAddressComponent {
	String long_name;
	String short_name;
	List<String> types;
}
class GCoordinates {
	String lat;
	String lng;
}
