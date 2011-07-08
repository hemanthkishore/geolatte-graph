/*
 * This file is part of the GeoLatte project.
 *
 *     GeoLatte is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     GeoLatte is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with GeoLatte.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright (C) 2010 - 2011 and Ownership of code is shared by:
 * Qmino bvba - Romeinsestraat 18 - 3001 Heverlee  (http://www.qmino.com)
 * Geovise bvba - Generaal Eisenhowerlei 9 - 2140 Antwerpen (http://www.geovise.com)
 */

package org.geolatte.graph;

import java.util.Arrays;

/**
 * Supports only one edge between two nodes!
 *
 * @author Karel Maesen, Geovise BVBA
 *         creation-date: Apr 25, 2010
 */
class NodeWrapper<N extends Located> implements Node<N> {

    final N wrappedNodal;
    NodeWrapper<N>[] toNodes = new NodeWrapper[0];
    EdgeWeight[] toWeights = new EdgeWeight[0];
    Object[] toLabels = new Object[0];
    private Node<N>[] fromNodes = new Node[0];

    NodeWrapper(N obj) {
        this.wrappedNodal = obj;
    }

    public N getWrappedNodal() {
        return this.wrappedNodal;
    }

    public void addEdge(Node<N> toNode, Object label, EdgeWeight edgeWeight) {

        // TODO : do not add multiple edges between the same pair of nodes
        // We only remember the last weight added

        //add the outgoing edge (complete information)
        toNodes = Arrays.copyOf(toNodes, toNodes.length + 1);
        toWeights = Arrays.copyOf(toWeights, toWeights.length + 1);
        toLabels = Arrays.copyOf(toLabels, toLabels.length + 1);
        toNodes[toNodes.length - 1] = (NodeWrapper) toNode;
        toWeights[toWeights.length - 1] = edgeWeight;
        toLabels[toLabels.length - 1] = label;

        //add the incoming edge info
        toNode.addReachableFrom(this);

    }


    protected Node<N>[] getConnected() {
        return this.toNodes;
    }

    protected Object getLabelToNode(N toNode) {
        for (int i = 0; i < this.toNodes.length; i++) {
            if (this.toNodes[i].wrappedNodal.equals(toNode)) {
                return this.toLabels[i];
            }
        }
        return null;
    }

    public int getX() {
        return this.wrappedNodal.getX();
    }

    public int getY() {
        return this.wrappedNodal.getY();
    }

    public String toString() {
        String str = String.format("MyNode: x = %d, y = %d", this.wrappedNodal.getX(), this.wrappedNodal.getY());
        return str;
    }

    protected Node<N>[] getReachableFrom() {
        return this.fromNodes;
    }

    public void addReachableFrom(Node<N> fromNode) {
        this.fromNodes = Arrays.copyOf(this.fromNodes, this.fromNodes.length + 1);
        this.fromNodes[this.fromNodes.length - 1] = fromNode;
    }

    public void getWeightTo(Node<N> toNode, int weightKind) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //NodeWrapper objects should correspond one-to-one to their WrappedNodes.
    //Equals/hashcode implementation is therefore not necessary. For
    // performance reasons, it should not be implemented.

}
