package com.carpool.android.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.carpool.android.R;
import com.google.android.gms.maps.model.LatLng;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class Util {

    public static void showMsgToastShort(Activity activity, String texto) {
        Toast.makeText(activity, texto, Toast.LENGTH_SHORT).show();
    }

    public static void showMsgToastLong(Activity activity, String texto) {
        Toast.makeText(activity, texto, Toast.LENGTH_LONG).show();
    }

    public static void showMsgAlertOk(Activity activity, String titulo, String texto) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(texto);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public static void showMsgAlertPosNeg(Activity activity, String titulo, String texto){
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(titulo);
        alertDialog.setMessage(texto);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public static void trocarTela(Activity activityAtual, Class activitySeguinte){
        Intent intent = new Intent(activityAtual, activitySeguinte);
        activityAtual.startActivity(intent);
    }

    public static void buildToolbarHomeButton(final AppCompatActivity activity, Toolbar toolbar){
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
    }

    public static void buildDrawer(final Activity activity, Toolbar toolbar) {
        final Activity activityFinal = activity;

        // Criação dos items e subitems do NavDrawer //
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withName(R.string.home)
                .withIcon(R.drawable.nav_item_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withName(R.string.perfil)
                .withIcon(R.drawable.nav_item_profile);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withName(R.string.procurar_carona)
                .withIcon(R.drawable.nav_item_procurar);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem()
                .withName(R.string.oferecer_carona)
                .withIcon(R.drawable.nav_item_oferecer);
        SecondaryDrawerItem item5 = new SecondaryDrawerItem()
                .withName(R.string.caronas)
                .withIcon(R.drawable.nav_item_preferences);
        SecondaryDrawerItem item6 = new SecondaryDrawerItem()
                .withName(R.string.pontos_favoritos)
                .withIcon(R.drawable.nav_item_preferences);
        SecondaryDrawerItem item7 = new SecondaryDrawerItem()
                .withName(R.string.logout)
                .withIcon(R.drawable.nav_item_exit);

        // Criação do cabeçalho do NavDrawer //
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activityFinal)
                .withHeaderBackground(R.color.colorPrimary)
                .addProfiles(
                        new ProfileDrawerItem()
                                .withName("Daniel Cândido")
                                .withEmail("candidosdaniel@gmail.com")
                                .withIcon(activityFinal.getResources().getDrawable(R.drawable.nav_profile))
                )
                .withProfileImagesClickable(false)
                .withSelectionListEnabled(false)
                .withAlternativeProfileHeaderSwitching(false)
                .build();

        // Inflando e construindo o NavDrawer //
        Drawer drawer = new DrawerBuilder()
                .withActivity(activityFinal)
                .withToolbar(toolbar)
                .withDrawerWidthDp(250)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        item1,
                        item2,
                        item3,
                        item4,
                        new DividerDrawerItem(),
                        item5,
                        item6,
                        item7
                )
                .withMultiSelect(false)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        acoesDrawer(activityFinal, view, position, drawerItem);
                        return false;
                    }
                })
                .build();

        //return drawer;
    }

    private static void acoesDrawer(Activity activity, View view, int position, IDrawerItem drawerItem){
        Util.showMsgToastShort(activity, "Posição: " + position);
        Intent i;
        switch (position) {
            case 1:
                i = new Intent(activity, MapsActivity.class);
                activity.startActivity(i);
                activity.finish();
                break;
            case 2:
                i = new Intent(activity, PessoaActivity.class);
                activity.startActivity(i);
                break;
            case 3:
                i = new Intent(activity, MapsActivity.class);
                activity.startActivity(i);
                activity.finish();
                break;
            case 4:
                i = new Intent(activity, MapsActivity.class);
                activity.startActivity(i);
                activity.finish();
                break;
            case 5:
                //
                break;
            case 6:
                showMsgToastShort(activity, activity.getString(R.string.em_desenvolvimento));
                break;
            case 7:
                showMsgToastShort(activity, activity.getString(R.string.em_desenvolvimento));
                break;
            case 8:
                activity.finish();
                break;
        }
    }

}
