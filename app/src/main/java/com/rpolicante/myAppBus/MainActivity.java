package com.rpolicante.myAppBus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.SwitchDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.OnCheckedChangeListener;
import com.rpolicante.myAppBus.Utils.DeviceHelper;
import com.rpolicante.myAppBus.domain.Bus;
import com.rpolicante.myAppBus.fragments.BusFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";

    private Toolbar mToolbar;
    private Toolbar mToolbarBottom;
    private Drawer navigationDrawer;
    private AccountHeader headerNavigation;

    private int mOldSelectedItemNavigation = 0;

    private OnCheckedChangeListener mOnCheckedChangeListener = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(IDrawerItem iDrawerItem, CompoundButton compoundButton, boolean b) {
            Toast.makeText(MainActivity.this, "onCheckedChanged: " + (b ? "True" : "False"), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_main_top);
        mToolbar.setTitle("MyBus");
        setSupportActionBar(mToolbar);

        mToolbarBottom = (Toolbar) findViewById(R.id.include_main_toolbar);
        mToolbarBottom.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Intent it = null;
                switch (menuItem.getItemId()) {
                    case R.id.menu_add_item:
                        it = new Intent(Intent.ACTION_VIEW);
                        it.setData(Uri.parse("http://www.google.com"));
                        break;
                }

                startActivity(it);

                return true;
            }
        });
        mToolbarBottom.inflateMenu(R.menu.menu_main);

        BusFragment frag = (BusFragment) getSupportFragmentManager().findFragmentByTag("busFrag");
        if (frag == null){
            frag = new BusFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.rl_main_container, frag, "busFrag");
            ft.commit();
        }

        //NAVIGATION HEADER
        headerNavigation = new AccountHeaderBuilder()
                .withActivity(this)
                .withCompactStyle(false)
                .withSavedInstance(savedInstanceState)
                .withThreeSmallProfileImages(false)
//                .withHeaderBackground(R.drawable.bus_img)
                .withCurrentProfileHiddenInList(true)
                .addProfiles(
                        new ProfileDrawerItem().withName("Rodrigo Martins").withEmail("policante.martins@gmail.com"),
                        new ProfileDrawerItem().withName("Visitante"))
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile iProfile, boolean b) {
                        Toast.makeText(MainActivity.this, "onProfileChanged: " + iProfile.getName(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                })
                .build();

        //NAVIGATION DRAWER
        navigationDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withDisplayBelowToolbar(false)
                .withActionBarDrawerToggleAnimated(true)
                .withDrawerGravity(Gravity.START)
                .withSavedInstance(savedInstanceState)
                .withSelectedItem(-1)
//                .withAccountHeader(headerNavigation)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
//                        for (int count = 0, tam = navigationDrawer.getDrawerItems().size(); count < tam; count++) {
//                            if (count == mOldSelectedItemNavigation && mOldSelectedItemNavigation <= 3) {
//                                PrimaryDrawerItem aux = (PrimaryDrawerItem) navigationDrawer.getDrawerItems().get(count);
//                                aux.setIcon(getResources().getDrawable( getSelectedIcon(position, selected) ));
//                            }
//                        }

                        mOldSelectedItemNavigation = i;
//                        navigationDrawer.getAdapter().notifyDataSetChanged();
                        return false;
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        return false;
                    }
                })
                .build();


        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Meus Onibus"));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Recentes"));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Novidades"));
        navigationDrawer.addItem(new SectionDrawerItem().withName("Configurações"));
        navigationDrawer.addItem(new SwitchDrawerItem().withName("Notificação").withChecked(true).withOnCheckedChangeListener(mOnCheckedChangeListener));
        navigationDrawer.addItem(new PrimaryDrawerItem().withName("Sobre"));
        navigationDrawer.addItem(new SectionDrawerItem().withName("Versão "+ DeviceHelper.getVersionName(this)));
    }

    public List<Bus> getSetBusList(int qtd) {

        String[] numbers = new String[]{"008","026","021","022","528"};
        String[] names   = new String[]{"Interbairros","Jd. Paulista", "Av. Tuiti", "Av. Guaiapó","Jd. Novo Oásis"};
        List<Bus> aux = new ArrayList<>();

        for (int i = 0; i < qtd; i++){
            Bus b = new Bus(numbers[i % numbers.length], names[i % numbers.length]);
            aux.add(b);
        }

        return aux;
    }
}
