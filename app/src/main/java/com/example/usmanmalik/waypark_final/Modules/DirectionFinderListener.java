package com.example.usmanmalik.waypark_final.Modules;

import java.util.List;

public interface DirectionFinderListener
{
    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
